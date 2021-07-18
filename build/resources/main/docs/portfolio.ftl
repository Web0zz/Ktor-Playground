<#-- @ftlvariable name="entries" type="kotlin.collections.List<com.web0zz.model.BlogEntry>" -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Title</title>
    <style>
        * {
            box-sizing: border-box;
        }
        li {
            display: inline;
        }
        #prf-image {
            display: block;
            margin-left: auto;
            margin-right: auto;
        }
        hr {
            alignment: center;
        }
    </style>
</head>
<body>
    <div class="profile-image">
        <img    id="prf-image"
                style= "border-radius: 50%; object-fit: cover;"
                src="images/profile.jpg" height="200" width="200" alt="profile-image"/>
    </div>
    <div class="body-item">
        <h1 style="text-align: center;">Furkan özen</h1>
        <ul>
            <li><a href="https://github.com"><img src="images/github.png" height="22"></a></li>
            <li><a href="https://github.com"><img src="images/linkedin.png" height="22"></a></li>
            <li><a href="https://twitter.com"><img src="images/twitter.png" height="22"></a></li>
            <li><a href="https://facebook.com"><img src="images/facebook.png" height="22"></a></li>
            <li><a href="https://instagram.com"><img src="images/instagram.png" height="22"></a></li>
        </ul>
    </div>
    <div>
        <h1 style="text-align: center;">MY BLOG</h1>
        <hr>
            <#list entries as item>
                <div>
                    <h3>${item.headline}</h3>
                    <p>${item.body}</p>
                </div>
            </#list>
        <hr>
    </div>
    <div>
        <h3>Add a new journal entry!</h3>
        <form action="/submit" method="post">
            <input type="text" name="headline">
            <br>
            <textarea name="body"></textarea>
            <br>
            <input type="submit">
        </form>
    </div>
</body>
</html>