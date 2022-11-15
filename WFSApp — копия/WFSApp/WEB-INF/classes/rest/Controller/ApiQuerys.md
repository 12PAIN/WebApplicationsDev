## Ping
#### GET /api/ returns "OK!" - Ping

## Products
#### GET /api/products/list/ - get product list

    Returns: 
        - 401 code If token expired 
        - 503 code if database is not available 
        - 400 code if json cannot be serialized 
        - 200 code with array of json products

#### POST /api/products/ - add product

    Returns: 
        - 401 code If token expired 
        - 503 code if database is not available 
        - 400 code if json cannot be serialized (data undefined)
        - 200 code with "row_added" in json format if success

#### DELETE /api/products/ - delete products

    Returns: 
        - 401 code If token expired 
        - 503 code if database is not available 
        - 400 code if json cannot be serialized 
        - 204 code if nothing to delete 
        - 406 code if input json is null 
        - 200 code with Int (count of deleted rows) in json format if success

## Users
#### POST api/users/auth/ - auth user with data

    Returns: 
        - 503 code if database is not available 
        - 400 code if json cannot be serialized 
        - 401 code if data not fits 
        - 200 code with token

#### POST api/users/ - add user

    Returns: 
        - 503 code if database is not available 
        - 400 code if json cannot be serialized 
        - 401 code if user already exit 
        - 200 code with text "userCreated" in json format
