const ad_id = $('#ad_id').val();

$('#changePassword').submit((event) => {
    event.preventDefault();

    let newPass = $('#newPassword').val();
    let newPassr = $('#newPasswordr').val();

    if (newPass == null || newPassr == null) {
        alert("Password cannot be defined blank.");
    } else {
        if (newPass != newPassr) {
            alert("The passwords you entered are different from each other.");
        } else if (newPass == newPassr) {
            console.log("Şifreler eşleşti.")
            //Admin'nin yeni şifresi belirlendi.

            const obj = {
                ad_id: ad_id,
                newPass: newPass,
            }

            $.ajax({
                url: './change-password-post',
                type: 'POST',
                data: {obj: JSON.stringify(obj)},
                dataType: 'JSON',
                success: function (data) {
                    if (data > 0) {
                        alert("İşlem Başarılı")
                        window.location = "http://localhost:8082/depo_project_war_exploded/";
                    } else {
                        alert("İşlem sırasında hata oluştu!");
                    }
                },
                error: function (err) {
                    console.log(err)
                    alert("İşlem işlemi sırasında bir hata oluştu!");
                }
            })
        }
    }
})

