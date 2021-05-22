# BaldWars API

## Run Application:

### With Docker:

- Create a  `.env` file
- Set your environment variables following `.env.example`
- Execute the command: `docker-compose up`

### Locally: 

You can use H2 database, for this you will need to set the following environment variables before running the application:

```dotenv
POSTGRES_URL=jdbc:h2:mem:local_db
POSTGRES_USER=local
POSTGRES_PASSWORD=local
JWT_TOKEN_SECRET="Bearer localToken"
```