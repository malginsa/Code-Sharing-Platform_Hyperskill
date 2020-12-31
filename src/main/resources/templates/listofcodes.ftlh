<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Latest</title>
</head>
<body>
<#list codes as codeSnippet>
    <span id="load_date">${codeSnippet.getFormattedDate()}</span>
    <pre id="code_snippet">${codeSnippet.code}</pre>
    <br>
</#list>
</body>
</html>
