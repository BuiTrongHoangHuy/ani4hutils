plan:
	@cd ./terraform/infrastructure && terraform plan
apply:
	@cd ./terraform/infrastructure && terraform apply
show-config:
	@cd ./terraform/infrastructure && terraform output
apply-dns:
	@cd ./terraform/cloudflare && terraform apply
proto:
	@cd ./api/shared/src/main/java && buf generate
tunnel:
	./.tunnel.sh
client-ui:
	@cd ./client-ui && npm run dev