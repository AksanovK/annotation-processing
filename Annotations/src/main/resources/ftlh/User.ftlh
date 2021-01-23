<#ftl encoding='UTF-8'>
<!doctype html>
<html lang="en">
<body>
<form method="${form.method}" action="${form.action}">
    <#list inputs as input1>
        <input type="${input1.type}" name="${input1.name}" placeholder="${input1.placeholder}">
    </#list>
</form>
</body>
</html>