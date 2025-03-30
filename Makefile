plan:
	@cd ./terraform/infrastructure && terraform plan
apply:
	@cd ./terraform/infrastructure && terraform apply
show-config:
	@cd ./terraform/infrastructure && terraform output
proto:
	@cd ./api/shared/src/main/java && buf generate