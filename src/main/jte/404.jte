@param String errorMessage

@template.layout.error(
    title = "404 Not Found",
    content = @`
        <h1>404</h1>
        @if(errorMessage != null && !errorMessage.isEmpty())
            <p style="color: red">${errorMessage}</p>
        @endif
        <p>Oops! The page you're looking for doesn't exist.</p>
        <p><a href="/">Go back to the homepage</a></p>
    `)

<%--<!DOCTYPE html>--%>
<%--<html lang="en">--%>
<%--    <head>--%>
<%--        <meta charset="UTF-8">--%>
<%--        <meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
<%--        <title>404 Not Found</title>--%>
<%--        <style>--%>
<%--            body {--%>
<%--              background-color: #f3f3f3;--%>
<%--              color: #333;--%>
<%--              font-family: Arial, sans-serif;--%>
<%--              text-align: center;--%>
<%--              padding: 50px;--%>
<%--            }--%>
<%--            h1 {--%>
<%--              font-size: 50px;--%>
<%--            }--%>
<%--            p {--%>
<%--              font-size: 20px;--%>
<%--            }--%>
<%--            a {--%>
<%--              text-decoration: none;--%>
<%--              color: #007bff;--%>
<%--            }--%>
<%--            a:hover {--%>
<%--              text-decoration: underline;--%>
<%--            }--%>
<%--        </style>--%>
<%--    </head>--%>
<%--    <body>--%>
<%--        <h1>404</h1>--%>
<%--        @if(errorMessage != null && !errorMessage.isEmpty())--%>
<%--            <p style="color: red">${errorMessage}</p>--%>
<%--        @endif--%>
<%--        <p>Oops! The page you're looking for doesn't exist.</p>--%>
<%--        <p><a href="/">Go back to the homepage</a></p>--%>
<%--    </body>--%>
<%--</html>--%>
