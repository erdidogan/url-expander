#!/usr/bin/env sh
# Deploy For Heroku Container Registery
echo "Deploy For Heroku Container Registery Started"

#Get Image ID
echo "Get IMAGE_ID"
IMAGE_ID=$(docker images --filter=reference=erdiapps/url-expander-api --format "{{.ID}}")

heroku container:login

docker tag "$IMAGE_ID" registry.heroku.com/url-expander-api/web

docker push registry.heroku.com/url-expander-api/web

heroku container:release web -a url-expander-api

echo "DEPLOY DONE"
