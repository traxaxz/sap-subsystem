#!/bin/sh
echo "🔐 Setting up Vault..."

if [ -f "/vault/config/.env" ]; then
    export $(grep -v '^#' /vault/config/.env | xargs)
fi

while ! vault status > /dev/null 2>&1; do
  echo "⏳ Waiting for Vault..."
  sleep 2
done

echo "✅ Vault is ready!"

export VAULT_TOKEN="root"

vault secrets enable -path=github kv || echo "✅ Secrets engine already enabled."

vault kv put github/api token="${GITHUB_ACCESS_TOKEN}" owner="${GITHUB_OWNER}"

echo "✅ Vault Setup Complete! Secrets stored securely."
