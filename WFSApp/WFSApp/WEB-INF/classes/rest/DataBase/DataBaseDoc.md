### Methods

1. boolean isUserCorrect(String login, String password) 
    Method gets user login and password.

    Returns: 
        - Null if connection is null or closed or invalid
        - False if user data is invalid and doesn't fit to the users
        - True if user data is correct

2. boolean createUser(String login, String password, String email)
    Method obtain data and post them into DataBase

    Returns:
        - Null if conn is invalid
        - False if this user already exist
        - True if user posted

3. Integer deleteRows(ArrayList of Integer)
    Method obtain list of int values of id's of items to delete.

    Returns: 
        - Null if conn is invalid
        - 0 if noting to delete
        - (RowsCount) int. Count of deleted rows

4. Integer addRow(String name, Integer price, String description)
    Method obtains data of single product and then post it on database

    Returns:
        - Null if conn is invalid
        - 0 if data is null
        - 1 if data inserted

5. Arraylist (ArrayList (String)) selectProducts
    Produces SQL select query to select all table of Products

    Returns:
        - Null if conn is invalid
        - Arraylist (ArrayList (String)), where ArrayList(String) contains 4 items - id, name, price, description