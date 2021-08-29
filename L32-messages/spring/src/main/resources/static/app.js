let stompClient = Stomp.over(new SockJS('/gs-guide-websocket'));

stompClient.connect({}, () => {
    stompClient.subscribe('/topic/clients/all', (message) => {
        $("#clientTable tbody tr").remove()
        showClients(JSON.parse(message.body));
    })
    stompClient.send("/app/message/client/getAll", {}, {})
});

const sendClient = () => {
    let client = {};
    client.name = document.getElementById("clientName").value;
    client.login = document.getElementById("clientLogin").value;
    client.role = document.getElementById("clientRole").value;
    stompClient.send("/app/message/client/save", {}, JSON.stringify(client))
}

const sendGetMessage = () => stompClient.send("/app/message/client/getAll", {}, {})

const showClients = (clients) => clients.forEach((client) => {
    $("#clientTableInner").append(
        "<tr><td>" + client.id +
        "</td><td>" + client.name +
        "</td><td>" + client.login +
        "</td><td>" + client.role +
        "</td></tr>")
})

$(function () {
    $("form").on('submit', (event) => {
        event.preventDefault();
    });
    $("#getClients").click(sendGetMessage);
    $("#addClient").click(sendClient);
});
