### Ping
1. GET /api/ returns "OK!" - Ping

### Products
2. GET /api/products/list/ - get product list

    Returns: \
        - 401 code If token expired \
        - 503 code if database is not available \
        - 400 code if json cannot be serialized \
        - 200 code with array of json products

3. POST /api/products/ - add product

    Returns: \
        - 401 code If token expired \
        - 503 code if database is not available \
        - 400 code if json cannot be serialized \
        - 200 code with "row_added" in json format if success

4. DELETE /api/products/ - delete products

    Returns: \
        - 401 code If token expired \
        - 503 code if database is not available \
        - 400 code if json cannot be serialized \
        - 204 code if nothing to delete \
        - 406 code if input json is null \
        - 200 code with Int (count of deleted rows) in json format if success

### Users
5. POST api/users/auth/ - auth user with data

    Returns: \
        - 503 code if database is not available \
        - 400 code if json cannot be serialized \
        - 401 code if data not fits \
        - 200 code with token

6. POST api/users/ - add user

    Returns: \
        - 503 code if database is not available \
        - 400 code if json cannot be serialized \
        - 401 code if user already exit \
        - 200 code with text "userCreated" in json format
