.PHONY: rake
rake:
	docker exec -i cloudstorage-db psql -U postgres -d registration < ./src/main/resources/rake.sql

.PHONY: restore
restore:
	docker exec -i cloudstorage-db psql -U postgres -d registration < ./src/main/resources/schema.sql 