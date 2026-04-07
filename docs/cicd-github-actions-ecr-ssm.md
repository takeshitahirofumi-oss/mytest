# CI/CD with GitHub Actions + ECR + SSM

This project can deploy automatically from GitHub to EC2 with Docker image storage in ECR.

## Architecture
- Source: GitHub (main branch)
- CI/CD runner: GitHub Actions
- Container registry: Amazon ECR
- App server: Amazon EC2 (Docker)
- DB: Amazon RDS
- Deployment channel: AWS Systems Manager Run Command (SSM)

## Added workflow
- .github/workflows/deploy-ec2.yml

Pipeline flow:
1. Run tests (`./mvnw test`)
2. Build Docker image
3. Push image to ECR
4. Run remote deployment commands on EC2 via SSM

## Required GitHub settings
Create these repository-level settings.

### Secrets
- `AWS_ROLE_TO_ASSUME`:
  - IAM Role ARN assumed by GitHub OIDC
  - Example: `arn:aws:iam::<account-id>:role/github-actions-deploy-role`

### Variables
- `AWS_REGION`:
  - Example: `ap-northeast-1`
- `ECR_REPOSITORY`:
  - Example: `ecsite`
- `EC2_INSTANCE_ID`:
  - Example: `i-0123456789abcdef0`

## Required AWS setup

### 1) IAM role for GitHub Actions (OIDC)
Attach permissions required for:
- ECR push/pull (`ecr:*` limited to your repository)
- SSM Run Command on target instance (`ssm:SendCommand`, `ssm:GetCommandInvocation`)
- Optional read permissions for SSM documents

Use GitHub OIDC provider and trust policy for your repository branch.

### 2) EC2 instance profile role
Attach role to EC2 with permissions:
- `AmazonSSMManagedInstanceCore`
- ECR pull permissions (for example `AmazonEC2ContainerRegistryReadOnly`)

### 3) EC2 prerequisites
- SSM Agent installed and online in Systems Manager
- Docker installed and running
- File `/opt/ecsite/.env` exists

Example `/opt/ecsite/.env`:
```
SPRING_DATASOURCE_URL=jdbc:postgresql://<rds-endpoint>:5432/<db-name>
SPRING_DATASOURCE_USERNAME=<db-user>
SPRING_DATASOURCE_PASSWORD=<db-password>
```

## How deployment works on EC2
The workflow sends these operations through SSM:
1. Login to ECR
2. Pull new image (`<ecr>/<repo>:<github-sha>`)
3. Stop old container
4. Start new container with `--env-file /opt/ecsite/.env`

## Triggering deployment
- Automatic: push to `main`
- Manual: run `workflow_dispatch` from Actions tab

## Notes
- If the workflow fails at deployment, check SSM command output in the GitHub Actions log.
- If your app port differs, change `APP_PORT` in workflow env.
- If you later add blue/green deployment, migrate to CodeDeploy or ECS.
