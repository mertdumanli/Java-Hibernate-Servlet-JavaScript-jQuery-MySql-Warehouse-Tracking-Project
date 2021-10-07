let selectedCustomer = 0;
let selectedVoucher = 0
let maxNumber = 0;
let globalxVoucher = 0;


$('#payInForm').submit((event) => {
    event.preventDefault();


    const obj = {
        selectedVoucher: selectedVoucher,
        paymentTotal: $("#payInTotal").val(),
        paymentDetail: $("#payInDetail").val(),
    }

    $.ajax({
        url: './pay-in-post',
        type: 'POST',
        data: {obj: JSON.stringify(obj)},
        dataType: 'JSON',
        success: function (data) {
            if (data > 0) {
                alert("İşlem Başarılı")
                xPaymentHistory();
                changeMaxNumberContinue(1)
            } else {
                alert("İşlem sırasında hata oluştu!\n(Boş bırakılan kısım / kısımlar mevcut.)");
            }
        },
        error: function (err) {
            console.log(err)
            alert("İşlem işlemi sırasında bir hata oluştu!");
        }
    })
})


$("#customer_select").change(function () {
    selectedCustomer = this.value;
    xSelectedCustomer();//Seçilen kullanıcının tüm ödenmeyen fişlerini listeleyeceğim.
    changeMaxNumberContinue(0);
    $('#payInDetail').val("");
    xPaymentHistory();//Kullanıcının geçmiş ödemelerini listeleme.
})
let globalVoucher;

function xPaymentHistory() {
    if (selectedCustomer > 0) {
        $.ajax({
            url: './pay-in-get?cu_id=' + selectedCustomer,
            type: 'GET',
            dataType: 'JSON',
            success: function (data) {
                if (data) {
                    createRow(data)
                } else {
                    console.log("Daha önce müşteri hiç ödeme gerçekleştirmemiş.")
                }
            },
            error: function (err) {
                console.log(err)
            }
        })
    }
}

$("#voucher_select").change(function () {
    selectedVoucher = this.value;
    changeMaxNumber();
    $('#payInDetail').val("");
})

function changeMaxNumber() {
    if (selectedVoucher > 0) {
        $.ajax({
            url: './invoice-single-servlet?cu_id=' + selectedCustomer + '&in_id=' + selectedVoucher,
            type: 'GET',
            dataType: 'JSON',
            success: function (data) {
                if (data) {
                    globalxVoucher = data;
                    changeMaxNumberContinue(1);
                } else {
                    alert("Boş giriş mevcut...")
                }
            },
            error: function (err) {
                console.log(err)
            }
        })
    } else {
        changeMaxNumberContinue(0)
    }


}

function changeMaxNumberContinue(x) {
    if (x == 1) {
        const data = globalxVoucher;
        maxNumber = data.in_balance;
        $("#payInTotal").attr("max", maxNumber);
        $("#payInTotal").val(maxNumber);
    } else if (x == 0) {
        globalxVoucher = 0;
        maxNumber = 1;
        $("#payInTotal").attr("max", maxNumber);
        $("#payInTotal").val(maxNumber);
    }

}

//Ödeme Tutarı > 0
$("#payInTotal").keyup(function () {
    $("#payInTotal").attr("min", 1);
    if (maxNumber != 0) {
        $("#payInTotal").attr("max", maxNumber);
    } else {
        $("#payInTotal").attr("max", 1);
    }

    const value = $("#payInTotal").val();
    if (value < 1) {
        $("#payInTotal").val(1);
    } else if (value > maxNumber) {
        $("#payInTotal").val(maxNumber);
    }
})


// reset fnc
function fncReset() {
    selectedCustomer = 0;
    $('#customer_select').val(0);
    $('#customer_select').selectpicker("refresh");

    selectedVoucher = 0;
    $('#voucher_select').val(0);
    $('#voucher_select').selectpicker("refresh");

    $('#payInTotal').val("");
    $('#payInDetail').val("");

    console.log("reset")
    xPaymentHistory();
}


function xSelectedCustomer() {

    $.ajax({
        url: './invoice-servlet?cu_id=' + selectedCustomer,
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            if (data) {
                globalVoucher = data;
                showVouchers(data);
            } else {
                showVouchers(null);
                alert("Müşterinin ödenmemiş faturası bulunmamaktadır.")
            }
        },
        error: function (err) {
            console.log(err)
        }
    })
}

function showVouchers(data) {

    $("#voucher_select").find('option').remove();
    $('#voucher_select').append("<option value=" + 0 + " data-subtext=" + ">Seçim Yapınız</option>")

    for (let i = 0; i < data.length; i++) {
        const item = data[i];
        $('#voucher_select').append("<option value=" + item.in_id + " data-subtext=₺ " + item.in_total + ">" + item.voucherNo + "</option>");
    }

    $('#voucher_select').val(0);
    $('#voucher_select').selectpicker("refresh");
    selectedVoucher = 0;
}

function createRow(data) {

    let html = ``;
    let payments = data;
    for (let i = 0; i < payments.length; i++) {
        const item = payments[i];

        let date = item.pa_localDateTime.date;
        let time = item.pa_localDateTime.time;

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
                            <td>` + item.pa_id + `</td>
                            <td>` + item.voucherNo + `</td>
                            <td>` + time + `</td>
                            <td>` + date + `</td>
                            <td>` + item.pa_paid + ` ₺</td>
                            <td>` + item.in_balance + ` ₺</td>
                            <td>` + item.pa_detail + `</td>
                            <td>` + item.in_total + `₺</td>
                                        <td class="text-right" >
              <div class="btn-group" role="group" aria-label="Basic mixed styles example">
                <button onclick="fncPayInDelete(` + item.pa_id + `)" type="button" class="btn btn-outline-primary "><i class="far fa-trash-alt"></i></button>           
              </div>
            </td>
                            </tr>`;
    }
    $('#paymentsHistoryTable').html(html);
}

function fncPayInDelete(pa_id) {
    let answer = confirm("Silmek istediğinizden emin misniz?");
    console.log(pa_id);
    if (answer) {
        $.ajax({
            url: './pay-in-delete?pa_id=' + pa_id,
            type: 'DELETE',
            dataType: 'text',
            success: function (data) {
                if (data != "0") {
                    xPaymentHistory();
                    // fncReset();
                    // $('#paymentsHistoryTable').html("");
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
