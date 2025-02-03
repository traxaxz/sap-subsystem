#!/bin/sh

echo "🔐 Setting up Vault..."

while ! vault status > /dev/null 2>&1; do
  echo "⏳ Waiting for Vault..."
  sleep 2
done

echo "✅ Vault is ready!"

export VAULT_TOKEN="root"

vault secrets enable -path=github kv || echo "✅ Secrets engine already enabled."

vault kv put github/api token="ghp_your_actual_token_here" owner="traxaz"

echo "✅ Vault Setup Complete! Secrets stored securely."
