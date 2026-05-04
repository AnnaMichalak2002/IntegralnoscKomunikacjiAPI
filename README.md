# Testy API

## Uwaga
Komendy najlepiej kopiować bezpośrednio z pliku `README.md` lub z Worda.  
Nie zaleca się kopiowania ich z PDF, ponieważ PDF może zmieniać:
- cudzysłowy,
- myślniki,
- backticky,
- układ linii.

---

# Zadanie 2

## Zapytanie - poprawny podpis

```powershell
$pair = "ma59740:123456"
$base64 = [Convert]::ToBase64String([System.Text.Encoding]::ASCII.GetBytes($pair))

$headers = @{
    Authorization = "Basic $base64"
    "X-HMAC-SIGNATURE" = "e090a0908892f157d3d1e71f39d1bac40a2498e68a8e14b657400efdefc64170"
}

$response = Invoke-WebRequest -Method POST `
    -Uri "http://localhost:8080/users" `
    -Headers $headers `
    -ContentType "application/json" `
    -Body '{"requestHeader":{"requestId":"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa","sendDate":"2026-04-08T12:00:00Z"},"user":{"id":"11111111-1111-1111-1111-111111111111","name":"Anna","surname":"Michalak","age":24,"personalId":"92011165987","citizenship":"PL","email":"a_mich@gmail.com"}}'

$response.StatusCode
$response.Content
```

## Zapytanie - niepoprawny podpis

```powershell
$pair = "ma59740:123456"
$base64 = [Convert]::ToBase64String([System.Text.Encoding]::ASCII.GetBytes($pair))

$headers = @{
    Authorization = "Basic $base64"
    "X-HMAC-SIGNATURE" = "e000a0908892f157d3d1e71f39d1bac40a2498e68a8e14b657400efdefc64170"
}

try {
    $response = Invoke-WebRequest -Method POST `
        -Uri "http://localhost:8080/users" `
        -Headers $headers `
        -ContentType "application/json" `
        -Body '{"requestHeader":{"requestId":"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa","sendDate":"2026-04-08T12:00:00Z"},"user":{"id":"11111111-1111-1111-1111-111111111111","name":"Anna","surname":"Michalak","age":24,"personalId":"92011165987","citizenship":"PL","email":"a_mich@gmail.com"}}'

    $response.StatusCode
    $response.Content
} catch {
    $_.Exception.Response.StatusCode.value__
}
```

---

# Zadanie 3

## Zapytanie - poprawny podpis

```powershell
$pair = "pba_user:123456"
$base64 = [Convert]::ToBase64String([System.Text.Encoding]::ASCII.GetBytes($pair))

$headers = @{
    Authorization = "Basic $base64"
}

$response = Invoke-WebRequest -Method POST `
    -Uri "https://pba-auth-service-b392362e592b.herokuapp.com/oauth/token" `
    -Headers $headers `
    -ContentType "application/x-www-form-urlencoded" `
    -Body "grant_type=client_credentials"

$response.StatusCode
$response.Content

$json = $response.Content | ConvertFrom-Json
$token = $json.access_token

$headers = @{
    Authorization = "Bearer $token"
    "X-JWS-SIGNATURE" = "eyJhbGciOiJIUzI1NiJ9.eyJyZXF1ZXN0SGVhZGVyIjp7InJlcXVlc3RJZCI6ImJiYmJiYmJiLWJiYmItYmJiYi1iYmJiLWJiYmJiYmJiYmJiYiIsInNlbmREYXRlIjoiMjAyNi0wNC0yM1QxMDowMDowMFoifSwidXNlciI6eyJuYW1lIjoiQW5uYSIsInN1cm5hbWUiOiJNaWNoYWxhayIsImFnZSI6MjUsInBlcnNvbmFsSWQiOiI5MjAxMTE2NTk4NyIsImNpdGl6ZW5zaGlwIjoiUEwiLCJlbWFpbCI6ImFfbWljaEBnbWFpbC5jb20ifX0.3oDF_h2LGBKatRNmxd45FZ31ZcDYeYP2_hDBaULWjvs"
}

$response = Invoke-WebRequest -Method PUT `
    -Uri "http://localhost:8080/users/11111111-1111-1111-1111-111111111111" `
    -Headers $headers `
    -ContentType "application/json" `
    -Body '{"requestHeader":{"requestId":"bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb","sendDate":"2026-04-23T10:00:00Z"},"user":{"name":"Anna","surname":"Michalak","age":25,"personalId":"92011165987","citizenship":"PL","email":"a_mich@gmail.com"}}'

$response.StatusCode
$response.Content
```

## Zapytanie - niepoprawny podpis

```powershell
$pair = "pba_user:123456"
$base64 = [Convert]::ToBase64String([System.Text.Encoding]::ASCII.GetBytes($pair))

$headers = @{
    Authorization = "Basic $base64"
}

$response = Invoke-WebRequest -Method POST `
    -Uri "https://pba-auth-service-b392362e592b.herokuapp.com/oauth/token" `
    -Headers $headers `
    -ContentType "application/x-www-form-urlencoded" `
    -Body "grant_type=client_credentials"

$response.StatusCode
$response.Content

$json = $response.Content | ConvertFrom-Json
$token = $json.access_token

$headers = @{
    Authorization = "Bearer $token"
    "X-JWS-SIGNATURE" = "ayJhbGciOiJIUzI1NiJ9.eyJyZXF1ZXN0SGVhZGVyIjp7InJlcXVlc3RJZCI6ImJiYmJiYmJiLWJiYmItYmJiYi1iYmJiLWJiYmJiYmJiYmJiYiIsInNlbmREYXRlIjoiMjAyNi0wNC0yM1QxMDowMDowMFoifSwidXNlciI6eyJuYW1lIjoiQW5uYSIsInN1cm5hbWUiOiJNaWNoYWxhayIsImFnZSI6MjUsInBlcnNvbmFsSWQiOiI5MjAxMTE2NTk4NyIsImNpdGl6ZW5zaGlwIjoiUEwiLCJlbWFpbCI6ImFfbWljaEBnbWFpbC5jb20ifX0.3oDF_h2LGBKatRNmxd45FZ31ZcDYeYP2_hDBaULWjvs"
}

try {
    $response = Invoke-WebRequest -Method PUT `
        -Uri "http://localhost:8080/users/11111111-1111-1111-1111-111111111111" `
        -Headers $headers `
        -ContentType "application/json" `
        -Body '{"requestHeader":{"requestId":"bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb","sendDate":"2026-04-23T10:00:00Z"},"user":{"name":"Anna","surname":"Michalak","age":25,"personalId":"92011165987","citizenship":"PL","email":"a_mich@gmail.com"}}'

    $response.StatusCode
    $response.Content
} catch {
    $_.Exception.Response.StatusCode.value__
}
```

---

# Oczekiwane wyniki

## Zadanie 2
- poprawny `X-HMAC-SIGNATURE` → `201`
- niepoprawny `X-HMAC-SIGNATURE` → `422`

## Zadanie 3
- poprawny `X-JWS-SIGNATURE` → `200`
- niepoprawny `X-JWS-SIGNATURE` → `422`
