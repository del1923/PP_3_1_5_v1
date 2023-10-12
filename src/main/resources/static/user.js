const url = "http://localhost:8080/api/user/"

//console.log("формируем юзера")

currentUser = fetch(url).then((response) => response.json())
// Таблица
currentUser.then((user) =>{
    let roles = "";
    user.roles.forEach((role) => {
        roles += " ";
        roles += role.role.replace("ROLE_", "");
    });
    let result = "";
    result += `<tr>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.surname}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${roles}</td>
                   </tr>`;
    //console.log( result )
    document.getElementById("user-info-table").innerHTML = result;
})