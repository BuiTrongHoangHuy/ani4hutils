plan:
	@cd ./terraform/infrastructure && terraform plan
apply:
	@cd ./terraform/infrastructure && terraform apply
proto:
	@cd ./api/shared/src/main/java && buf generate