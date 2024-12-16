@param String errorMessage

@template.layout.error(
title = "403 Forbidden",
content = @`
    <h1>403</h1>
    @if(errorMessage != null && !errorMessage.isEmpty())
        <p style="color: red">${errorMessage}</p>
    @endif
    <p>Unauthorized!</p>
    <p><a href="/">Go back to the homepage</a></p>
`)