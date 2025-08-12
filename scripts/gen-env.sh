#!/bin/sh

# Genera 32 bytes aleatorios en Base64
if command -v openssl >/dev/null 2>&1; then
  SECRET="$(openssl rand -base64 32)"
elif command -v dd >/dev/null 2>&1; then
  SECRET="$(dd if=/dev/urandom bs=32 count=1 2>/dev/null | base64)"
else
  echo "No se encontró openssl ni dd+base64. Genera JWT_SECRET manualmente."
  exit 1
fi

cat > .env <<EOF
JWT_SECRET=$SECRET
EOF

echo "[OK] .env generado"