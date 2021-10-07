let checkBox = "unchecked";
$('#login_form').submit((event) => {
    event.preventDefault();

    const email = $("#email").val()
    const password = $("#password").val()

    if ($("#checkBox").prop("checked")) {
        checkBox = "checked"
    }

    const obj = {
        ad_email: email,
        ad_password: password,
    }

    $.ajax({
        url: './admin-post',
        type: 'POST',
        data: {obj: JSON.stringify(obj)},
        dataType: 'JSON',
        success: function (data) {
            if (data) {
                feedBackSuccessLogin(data)
            } else {
                feedBackErrorLogin()
            }
        },
        error: function (err) {
            console.log(err)
            alert("İşlem işlemi sırısında bir hata oluştu!");
        }
    })
})

function feedBackErrorLogin() {
    let html = `<div class="invalid-feedback" style="display: block" >Email ve şifre eşleşmedi.</div>`
    $('#feedBackLogin').html(html);
}

function feedBackSuccessLogin(data) {
    console.log(checkBox)
    if (checkBox == "checked") {
        document.cookie = "user=" + data.ad_id + "___" + data.ad_name + "___" + data.ad_surname + "___" + data.ad_email + "___" + data.ad_password + ";max-age=" + 60 * 60 * 30;
    }
    window.location = "http://localhost:8082/depo_project_war_exploded/dashboard.jsp";
}

$('#pass_send_btn').click(function (e) {
    const email = $("#pass_email").val();
    $.ajax({
        url: './admin-remember-post',
        type: 'POST',
        data: {obj: JSON.stringify(email)},
        dataType: 'JSON',
        success: function (data) {
            if (data) {
                feedBackSuccessRemember()
            } else {
                feedBackErrorRemember()
            }
        },
        error: function (err) {
            console.log(err)
            alert("İşlem işlemi sırasında bir hata oluştu!");
        }
    })
})

function feedBackSuccessRemember() {
    $('#pass_fail').html('');

    let html = `<div class="valid-feedback" style="display: block" >Mail gönderildi.</div>`
    $('#pass_success').html(html);
}

function feedBackErrorRemember() {
    $('#pass_success').html('');

    let html = `<div class="invalid-feedback" style="display: block">Email adresi mevcut değil veya işlem sırasında hata meydana geldi.</div>`
    $('#pass_fail').html(html);
}

