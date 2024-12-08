@param String errorMessage
@param String errorDetails
@param int errorStatus 

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Error!!!</title>
    <style>
        body {
          background-color: #f3f3f3;
          color: #333;
          font-family: Arial, sans-serif;
          text-align: center;
          padding: 50px;
        }
        h1 {
          font-size: 50px;
        }
        p {
          font-size: 20px;
        }
        a {
          text-decoration: none;
          color: #007bff;
        }
        a:hover {
          text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="">
        <h1>Đã xảy ra lỗi!</h1>
        <p>${errorMessage}</p>
        <p>Chi tiết lỗi: ${errorDetails}</p>
        <p>Mã lỗi: ${errorStatus}</p>
        <a href="/dashboard">Quay lại Dashboard</a>
    </div>
</body>
</html>
