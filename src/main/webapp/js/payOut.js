let globalArr = [];

function fncReset() {
    $('#payOutType').val(0);
    $('#payOutType').selectpicker("refresh");

    $('#payOutTitle').val("");
    $('#payOutTotal').val("");
    $('#payOutDetail').val("");

    console.log("reset")
}

$("#payOutTotal").keyup(function () {
    const value = this.value;
    if (value < 1) {
        $("#payOutTotal").val(1);
        $("#payOutTotal").attr("min", 1);
        //max değer ayarlamadım çünkü farklı ödeme şekilleri farklı parametreler mevcut.
    }
})


$('#payOutForm').submit((event) => {
    event.preventDefault();

    const title = $("#payOutTitle").val();
    const type = $("#payOutType").val();
    const total = $("#payOutTotal").val();
    const detail = $("#payOutDetail").val();

    const obj = {
        payOutTitle: title,
        payOutType: type,
        payOutTotal: total,
        payOutDetail: detail,
    }
    if (title != "" && type != 0 && total != "") {
        $.ajax({
            url: './pay-out-post',
            type: 'POST',
            data: {obj: JSON.stringify(obj)},
            dataType: 'JSON',
            success: function (data) {
                if (data > 0) {
                    alert("İşlem Başarılı")
                    xPaymentHistory();
                } else {
                    alert("İşlem sırasında hata oluştu! Veritabanı bağlantısını kontrol ediniz.");
                }
            },
            error: function (err) {
                console.log(err)
                alert("İşlem işlemi sırasında bir hata oluştu!");
            }
        })
    } else {
        window.alert("Ödeme detayı dışındaki tüm bilgilerin doldurulması gerekmektedir.");
    }
})

function xPaymentHistory() {

    $.ajax({
        url: './pay-out-get',
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            if (data) {
                globalArr = data;
                createRow(data)
            } else {
                console.log("Daha önce hiç ödeme gerçekleştirilmemiş.")
            }
        },
        error: function (err) {
            console.log(err)
        }
    })
}

xPaymentHistory();

function createRow(data) {
    let html = ``;
    let paymentsOut = data;
    console.log(data)
    for (let i = 0; i < paymentsOut.length; i++) {
        const item = paymentsOut[i];

        let date = item.localDateTime.date;
        let time = item.localDateTime.time;

        let saat = time.hour;
        if (parseInt(saat) <= 9) {
            saat = "0" + saat;
        }
        let dakika = time.minute;
        if (parseInt(dakika) <= 9) {
            dakika = "0" + dakika;
        }
        let saniye = time.second;
        if (parseInt(saniye) <= 9) {
            saniye = "0" + saniye;
        }
        let gun = date.day;
        if (parseInt(gun) <= 9) {
            gun = "0" + gun;
        }
        let ay = date.month;
        if (parseInt(ay) <= 9) {
            ay = "0" + ay;
        }


        time = saat + ":" + dakika + ":" + saniye;
        date = gun + "/" + ay + "/" + date.year;

        html += `           <tr role="row" class="odd">
                            <td>` + item.op_id + `</td>
                            <td>` + item.op_title + `</td>
                            <td>` + getTypeName(item.op_type) + `</td>
                            <td>` + item.op_detail + `</td>
                            <td>` + item.op_total + ` ₺</td>
                            <td>` + date + `</td>
                            <td>` + time + `</td>
                            <td>` + item.admin.ad_id + `</td>
                                                                    <td class="text-right" >
              <div class="btn-group" role="group" aria-label="Basic mixed styles example">
                <button onclick="fncPayInDelete(` + item.op_id + `)" type="button" class="btn btn-outline-primary "><i class="far fa-trash-alt"></i></button>
                             </div>
            </td>
                            </tr>`;
    }
    $('#paymentsOutHistoryTable').html(html);
}


function fncPayInDelete(op_id) {
    let answer = confirm("Silmek istediğinizden emin misniz?");
    console.log(op_id);
    if (answer) {
        $.ajax({
            url: './pay-out-delete?op_id=' + op_id,
            type: 'DELETE',
            dataType: 'text',
            success: function (data) {
                if (data != "0") {
                    window.location = "http://localhost:8082/depo_project_war_exploded/payOut.jsp";
                    // fncReset();
                    //xPaymentHistory();
                    //fncReset fonksiyonu ile ödeme türü butonu sıfırlandığı vakit görüntü bozulduğu için,
                    //bu şekilde sayfayı refresh yaptırmayı tercih ettim.
                } else {
                    alert("Silme sırasında bir hata oluştu!");
                }
            },
            error: function (err) {
                console.log(err)
            }
        })
    }
}

function getTypeName(i) {
    if (i == 1) {
        return "Nakit";
    } else if (i == 2) {
        return "Kredi Kartı"
    } else if (i == 3) {
        return "Havale";
    } else if (i == 4) {
        return "EFT";
    } else if (i == 5) {
        return "Banka Çeki";
    }
}