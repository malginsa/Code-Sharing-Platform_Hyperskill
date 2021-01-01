<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Latest</title>
</head>
<body>
<#list codes as codeSnippet>
    <span class="load_date">${codeSnippet.getFormattedDate()}</span>
    <pre class="code_snippet">${codeSnippet.code}</pre>
    <br>
</#list>
</body>
</html>
