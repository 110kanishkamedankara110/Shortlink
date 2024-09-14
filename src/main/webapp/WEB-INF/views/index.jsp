<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="res/img/icon.svg" />
    <title>JihLink</title>
    <style>
        /* CSS Reset */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            padding: 20px;
        }

        .container {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 500px;
        }

        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }

        .input-group {
            margin-bottom: 20px;
        }

        .input-group label {
            display: block;
            font-size: 15px;
            margin-bottom: 8px;
            color: #333;
        }

        .input-group input[type="text"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
            background-color: #fff;
        }

        .input-group button {
            width: 100%;
            border: none;
            padding: 12px;
            height: 100%;
            background-color: black;
            color: white;
            font-size: 16px;
            border-radius: 5px;
            cursor: pointer;
        }

        .input-group button:hover {
            background-color: #d2d2d2;
        }

        /* Input group for shortened URL */
        .input-group-url {
            display: flex;
            align-items: center;
            position: relative;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-sizing: border-box;
        }

        .input-group-url input[type="text"] {
            width: calc(100% - 40px);
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #fff;
        }

        .input-group-url button {
            position: absolute;
            background: none;
            border: none;
            cursor: pointer;
            width: fit-content;
            border-left: 1px solid #ddd;
            right: 0;
            padding: 5px;
            box-sizing: border-box;
        }

        .input-group-url button img {
            width: 24px;
            height: 24px;
        }

        .input-group-url button:hover img {
            opacity: 0.7;
        }

        .console {
            position: relative;
            background-color: #333;
            color: #fff;
            font-family: monospace;
            padding: 10px;
            border-radius: 5px;
            height: 200px;
            overflow-y: auto;
            margin-top: 20px;
        }

        #console-log {
            height:100%; /* Adjust to make space for button */
            overflow-y: auto;
        }

        #clear-console-btn {
            background-color: rgba(210, 210, 210, 0.45);
            border: none;
            position: absolute;
            top: 10px;
            right: 10px;
            border-radius: 5px;
            cursor: pointer;
        }

        #clear-console-btn img {
            width: 24px;
            height: 24px;
        }

        #clear-console-btn:hover img {
            opacity: 0.7;
        }
        /* Responsive Design */
        @media (max-width: 600px) {
            .container {
                padding: 15px;
            }

            .input-group input[type="text"], .input-group-url input[type="text"] {
                font-size: 14px;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <h1>JihLink</h1>

    <div class="input-group">
        <label for="original-url">Enter the URL to shorten:</label>
        <input type="text" id="original-url" placeholder="https://example.com"/>
    </div>

    <div class="input-group">
        <button id="generate-btn">Generate</button>
    </div>

    <div class="input-group input-group-url">
        <input style="border: none" disabled type="text" id="shorten-url" placeholder="Shortened URL"/>
        <button id="copy-btn" disabled>
            <img src="https://img.icons8.com/material-outlined/24/000000/copy.png" alt="Copy"/>
        </button>
    </div>

    <!-- Console to display status messages -->
    <div class="console">
        <button id="clear-console-btn">
            <img src="https://img.icons8.com/material-outlined/24/000000/erase.png" alt="Clear"/>
        </button>
        <div id="console-log">
        </div>
    </div>

</div>

<script>
    const logToConsole = (message) => {
        const consoleLog = document.getElementById('console-log');
        const newMessage = document.createElement('pre');

        // Check if message is a string and needs parsing
        let parsedMessage;
        try {
            parsedMessage = typeof message === 'string' ? JSON.parse(message) : message;
        } catch (e) {
            parsedMessage=message;
        }

        newMessage.innerHTML = formatJSON(parsedMessage); // Use formatted JSON
        consoleLog.appendChild(newMessage);
        consoleLog.scrollTop = consoleLog.scrollHeight;  // Auto-scroll to the bottom
    };

    document.getElementById('generate-btn').addEventListener('click', function () {

        const originalUrl = document.getElementById('original-url').value;
        const shortenUrlInput = document.getElementById('shorten-url');
        const copyButton = document.getElementById('copy-btn');

        if (originalUrl.trim() === "") {
            logToConsole("Error: No URL entered.");
            return;
        }
        const consoleLog = document.getElementById('console-log');
        consoleLog.innerHTML = ''; // Clear all content
        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/x-www-form-urlencoded");

        const urlencoded = new URLSearchParams();
        urlencoded.append("link", originalUrl);

        const requestOptions = {
            method: "POST",
            headers: myHeaders,
            body: urlencoded,
            redirect: "follow"
        };

        fetch("api/create", requestOptions)
            .then((response) =>
                response.json()
            )
            .then((result) => {
                if(result.status=="success"){
                    shortenUrlInput.value=result.data;
                    copyButton.disabled = false;
                }

                logToConsole(result);

            })
            .catch((error) => {
                console.error(error)
            });



    });

    const formatJSON = (json) => {
        // Format JSON with indentation
        const formatted = JSON.stringify(json, null, 2);

        // Apply syntax highlighting
        return formatted
            .replace(/&/g, '&amp;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;')

            .replace(/"(.*?)"/g, '<span class="string">"$1"</span>')
            .replace(/(\d+)/g, '<span class="number">$1</span>')
            .replace(/(true|false)/g, '<span class="boolean">$1</span>')
            .replace(/(null)/g, '<span class="null">$1</span>');
    };


    document.getElementById('copy-btn').addEventListener('click', function () {
        const shortenUrlInput = document.getElementById('shorten-url');

        // Copy the shortened URL to the clipboard
        shortenUrlInput.select();
        shortenUrlInput.setSelectionRange(0, 99999); // For mobile devices

        navigator.clipboard.writeText(shortenUrlInput.value)
            .then(() => {
                logToConsole("Short URL copied to clipboard!");
            })
            .catch(err => {
                logToConsole("Failed to copy URL.");
                console.error('Failed to copy text: ', err);
            });
    });

    document.getElementById('clear-console-btn').addEventListener('click', function () {
        const consoleLog = document.getElementById('console-log');
        consoleLog.innerHTML = ''; // Clear all content
        logToConsole("Console cleared.");
    });
</script>

</body>
</html>
