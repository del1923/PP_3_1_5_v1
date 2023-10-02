const url = 'http://localhost:8080/api/admin';


function getUserData() {
    fetch(url)
        .then(res => res.json())
        .then(data => {
            loadTable(data)
        })
}

function getAllUsers() {
    fetch(url).then(response => response.json()).then(user =>
        loadTable(user))
}

function loadTable(listAllUsers) {
    let res = '';
    for (let user of listAllUsers) {
        res +=
            `<tr>
                <td>${user.id}</td>             
                <td>${user.name}</td>
                <td>${user.surname}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td id=${'role' + user.id}>${user.role.map(r => r.role.substring(5)).join(', ')}</td>
                <td>
                    <button class="btn btn-info" type="button"
                    data-bs-toggle="modal" data-bs-target="#editModal"
                    onclick="editModal(${user.id})">Edit</button></td>
                <td>
                    <button class="btn btn-danger" type="button"
                    data-bs-toggle="modal" data-bs-target="#deleteModal"
                    onclick="deleteModal(${user.id})">Delete</button></td>
            </tr>`
    }
    document.getElementById('tableBodyAdmin').innerHTML = res;
}

getAllUsers();

// Новый юзер
document.getElementById('newUserForm').addEventListener('submit', (e) => {
    e.preventDefault()
    let role = document.getElementById('role_select')
    let rolesAddUser = []
    let rolesAddUserValue = ''
    for (let i = 0; i < role.options.length; i++) {
        if (role.options[i].selected) {
            rolesAddUser.push({id: role.options[i].value, name: 'ROLE_' + role.options[i].innerHTML})
            rolesAddUserValue += role.options[i].innerHTML
        }
    }
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
            name: document.getElementById('newName').value,
            surname: document.getElementById('newSurname').value,
            age: document.getElementById('newAge').value,
            email: document.getElementById('newEmail').value,
            password: document.getElementById('newPassword').value,
            role: rolesAddUser
        })
    })
        .then((response) => {
            if (response.ok) {
                getUserData()
                document.getElementById("all-users-tab").click()
            }
        })
})

//Изменение юзера
function editModal(id) {
    fetch(url + '/' + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => {
        res.json().then(u => {

            document.getElementById('editId').value = u.id;
            document.getElementById('editName').value = u.name;
            document.getElementById('editSurName').value = u.surname;
            document.getElementById('editAge').value = u.age;
            document.getElementById('editEmail').value = u.email;
            document.getElementById('editPassword').value = "****";

        })
    });
}


async function editUser() {
    const form_ed = document.getElementById('modalEdit');

    let idValue = document.getElementById("editId").value;
    let nameValue = document.getElementById("editName").value;
    let surnameValue = document.getElementById("editSurName").value;
    let ageValue = document.getElementById("editAge").value;
    let emailValue = document.getElementById("editEmail").value;
    let passwordValue = document.getElementById("editPassword").value;
    let listOfRole = [];
    for (let i = 0; i < form_ed.role.options.length; i++) {
        if (form_ed.role.options[i].selected) {
            let tmp = {};
            tmp["id"] = form_ed.role.options[i].value
            listOfRole.push(tmp);
        }
    }
    let user = {
        id: idValue,
        name: nameValue,
        surname: surnameValue,
        age: ageValue,
        email: emailValue,
        password: passwordValue,
        role: listOfRole
    }
    await fetch(url + '/' + user.id, {
        method: "PUT",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify(user)
    });
    closeModal()
    getUserData()
}

// Удаление юзера
function deleteModal(id) {
    fetch(url + '/' + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => {
        res.json().then(u => {
            document.getElementById('deleteId').value = u.id;
            document.getElementById('deleteName').value = u.name;
            document.getElementById("deleteSurName").value = u.surname;
            document.getElementById('deleteAge').value = u.age;
            document.getElementById("deleteEmail").value = u.email;
            document.getElementById("deleteRole").value = u.role.map(r => r.role.substring(5)).join(", ");
        })
    });
}

async function deleteUser() {
    const id = document.getElementById("deleteId").value
    console.log(id)
    let urlDel = url + "/" + id;
    let method = {
        method: 'DELETE',
        headers: {
            "Content-Type": "application/json"
        }
    }

    fetch(urlDel, method).then(() => {
        closeModal()
        getUserData()
    })
}

function closeModal() {
    // document.getElementById("editClose").click()
    document.querySelectorAll(".btn-close").forEach((btn) => btn.click())
}