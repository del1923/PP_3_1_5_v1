const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if (e.target.closest(selector)) {
            handler(e.target)
        }
    })
}

const urlAdmin = "/api/admin/"
// const urlAdmin = "https:/localhost:8080/api/admin/"
currentUser = fetch( urlAdmin ).then((response) => response.json())

const urlAllUser = "/api/admin/users"

const allUser = fetch(urlAllUser)
    .then((response) => response.json())

allUser.then(listUsers => {
        let result = ''
        for (const i in listUsers) {
            let roles = ''
            listUsers[i].roles.forEach(role => {
                roles += ' '
                roles += role.role.replace("ROLE_", "")
            })
            result += `<tr>
                    <td>${listUsers[i].id}</td>
                    <td>${listUsers[i].name}</td>
                    <td>${listUsers[i].surname}</td>
                    <td>${listUsers[i].age}</td>
                    <td>${listUsers[i].email}</td>
                    <td>${roles}</td>
                    <td>
                        <button type="button" class="btn btn-primary btn-sm text-white" id="editUserBtn">Edit</button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-danger btn-sm" id="deleteUserBtn">Delete</button>
                    </td>
                    </tr>`
        }
        document.getElementById("users-table").innerHTML = result
    }
)


const urlRole = "/api/admin/roles"
const listRoles = fetch(urlRole).then(response => response.json())
const fillRole = function (elementId) {
    listRoles.then(roles => {
        let result = ''
        for (const i in roles) {
            result += `<option value=${roles[i].id}>${roles[i].role.replace("ROLE_", "")}
                       </option>`
        }
        document.getElementById(elementId).innerHTML = result
    })
}

fillRole("role_select")

//__________________________________________________________________________________


const urlPost = "/api/admin/add"

const newUserForm = document.getElementById("newUserForm")
document.getElementById("newUserForm")
    .addEventListener("submit", (e) => {
        e.preventDefault()
        let nameRole = document.getElementById("role_select")
        let listRoles = []
        let roleValue = ""
        for (let i = 0; i < nameRole.options.length; i++) {
            if (nameRole.options[i].selected) {
                listRoles.push({
                    id: nameRole.options[i].value,
                    role: "ROLE_" + nameRole.options[i].innerHTML
                })
                roleValue += nameRole.options[i].innerHTML + ''
            }
        }
        fetch(urlPost, {
            method: "POST",
            headers: {
                "Content-type": "application/json"
            },
            body: JSON.stringify({
                name: document.getElementById("name").value,
                surname: document.getElementById( "surname").value,
                age: document.getElementById("age").value,
                email:document.getElementById( "email").value,
                password: document.getElementById("password").value,
                roles: listRoles
            })
        }).then(() => {
            newUserForm.reset()
        })
        document.getElementById("all-users-tab").click()
    })
//_______________________________________________________________________________________
pageUser = fetch("/api/admin/").then(response => response.json())
pageUser.then((user) => {
    let rol = "";
    user.roles.forEach((name) => {
        rol += " ";
        rol += name.role.replace("ROLE_", "");
    });

    let result = "";
    result += `<tr>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.surname}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${rol}</td>
                   </tr>`;
    document.getElementById("user-table").innerHTML = result;
})


const urlPATCH = "/api/admin/update"

const editUserModel = new bootstrap.Modal(document.getElementById("editUserModal"))


const editId = document.getElementById("id_edit")
const editname = document.getElementById("name_edit")
const editsurname = document.getElementById( "surname_edit")
const editage = document.getElementById("age_edit")
const editemail = document.getElementById( "email_edit")
const editPassword = document.getElementById("password_edit")
const editRole = document.getElementById("role_edit")

const formEdit = document.getElementById("edit_user_form")

//модальное окно Edit
on(document, 'click', '#editUserBtn', e => {
    const fila = e.parentNode.parentNode
    let option = ''
    editId.value = fila.children[0].innerHTML
    editname.value = fila.children[1].innerHTML
    editsurname.value = fila.children[2].innerHTML
    editage.value = fila.children[3].innerHTML
    editemail.value = fila.children[4].innerHTML
    editPassword.value = fila.children[5].innerHTML
    listRoles.then(rolList => {
        rolList.forEach(name => {
            let selected = fila.children[6].innerHTML.includes(name.role.replace('ROLE_', '')) ? 'selected' : ''
            option += `
                <option value="${name.id}" ${selected}>${name.role.replace('ROLE_', '')}</option>`

        })
        editRole.innerHTML = option
    })
    editUserModel.show()
})

formEdit.addEventListener('submit', e => {
    e.preventDefault()
    let nameRoleEdit = document.getElementById("role_edit")
    let listRoleEdit = []
    let roleValueEdit = ''

    for (let i = 0; i < nameRoleEdit.options.length; i++) {
        if (nameRoleEdit.options[i].selected) {
            listRoleEdit.push({id: nameRoleEdit.options[i].value, role: 'ROLE_' + nameRoleEdit.options[i].innerHTML})
            roleValueEdit += nameRoleEdit.options[i].innerHTML + ' '
        }
    }

    fetch(urlPATCH, {
        method: "PATCH",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: editId.value,
            name: editname.value,
            surname: editsurname.value,
            age: editage.value,
            email: editemail.value,
            password: editPassword.value,
            roles: listRoleEdit
        })
    })
    editUserModel.hide()
})

const urlDelete = "/api/admin/delete/"

const deleteModalBtn = new bootstrap.Modal(document.getElementById("deleteUserModal"))


let rowDelete = null
on(document, 'click', '#deleteUserBtn', e => {
    rowDelete = e.parentNode.parentNode
    document.getElementById('id_delete').value = rowDelete.children[0].innerHTML
    document.getElementById('name_delete').value = rowDelete.children[1].innerHTML
    document.getElementById("surname_delete").value = rowDelete.children[2].innerHTML
    document.getElementById('age_delete').value = rowDelete.children[3].innerHTML
    document.getElementById("email_delete").value = rowDelete.children[4].innerHTML

    let option = ''
    listRoles.then(roles => {
        roles.forEach(role => {
            if (rowDelete.children[5].innerHTML.includes(role.role.replace('ROLE_', ''))) {
                option += `<option value="${role.id}">${role.role.replace('ROLE_', '')}</option>`
            }
        })
        document.getElementById('role_delete').innerHTML = option
    })
    deleteModalBtn.show()
})

document.getElementById('delete_user_form').addEventListener('submit', (e) => {
    e.preventDefault()
    fetch(urlDelete + rowDelete.children[0].innerHTML, {
        method: 'DELETE'
    }).then(() => {
        deleteModalBtn.hide()
        rowDelete.parentNode.removeChild(rowDelete)
    })
})