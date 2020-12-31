<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Latest</title>
    </head>
<body>
    <#list codes as codeElement>
        <span id="load_date">${codeElement.date}</span>
        <pre id="code_snippet">${codeElement.code}</pre>
        <br>
    </#list>
</body>
</html>
