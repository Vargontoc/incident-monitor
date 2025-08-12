# Requiere PowerShell 5+ (mejor 7+)
# Genera 32 bytes aleatorios y escribe .env en UTF-8 sin BOM
$bytes = New-Object byte[] 32
[Security.Cryptography.RandomNumberGenerator]::Create().GetBytes($bytes)
$secret = [Convert]::ToBase64String($bytes)

$envContent = @"
JWT_SECRET=$secret
"@

# UTF8 sin BOM
Set-Content -Path ".env" -Value $envContent -Encoding UTF8
Write-Host "[OK] .env generado en UTF-8 sin BOM"