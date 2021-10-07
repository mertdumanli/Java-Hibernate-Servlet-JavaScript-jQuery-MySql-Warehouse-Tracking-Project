let cu_id = $("#customer_select").val();
let type = $("#type").val();
let startDate;
let endDate;
console.log(cu_id);
console.log(type);

$("#customer_select").change(function () {
    cu_id = this.value;
    console.log("customer : " + this.value)
})

$("#type").change(function () {
    type = this.value;
    console.log("type : " + this.value)
})

$("#startDate").change(function () {
    startDate = this.value;
    console.log("startDate : " + this.value)
})

$("#endDate").change(function () {
    endDate = this.value;
    console.log("endDate : " + this.value)
})

$("#shownReport").submit((event) => {
    event.preventDefault();

    obj = {
        cu_id: cu_id,
        type: type,
        startDate: startDate,
        endDate: endDate
    }

    $.ajax({
        url: './check-out-actions-post',
        type: 'POST',
        data: {obj: JSON.stringify(obj)},
        dataType: 'JSON',
        success: function (data) {
            if (data) {
                shownReports(data);
            } else {
                console.log("Arama sonucu boş.");
            }
        },
        error: function (err) {
            console.log(err)
            alert("İşlem işlemi sırasında bir hata oluştu!");
        }
    })
})

function removeTables() {
    $('#inTypeThead').html("");
    $('#inTypeTbody').html("");
    $('#outTypeThead').html("");
    $('#outTypeTbody').html("");
}


function shownReports(data) {
    console.log(data);
    if (cu_id == 0) {
        if (type == 1) {
            removeTables();
            createPayInTable(data);
        } else if (type == 2) {
            removeTables();
            createPayOutTable(data);
        } else {
            removeTables();
            createPayInTable(data[0]);
            createPayOutTable(data[1]);
        }
    } else if (cu_id != 0) {
        if (type == 0 || type == 1) {
            removeTables();
            createPayInTable(data);
        } else {
            removeTables();
            console.log("Empty");
        }
    }
}

function createPayInTable(data) {
    let html = ``;
    if (data.length > 0) {
        html += `    <tr>
                        <th>Payment ID</th>
                        <th>Fiş Numarası</th>
                        <th>Müşteri Kodu</th>
                        <th>Müşteri İsmi</th>
                        <th>Ödeme Zamanı</th>
                        <th>Ödeme Tarihi</th>
                        <th>Ödenen Tutar</th>
                        <th>Toplam Kalan Tutar</th>
                        <th>Ödeme Detayı</th>
                        <th>Toplam Fatura Tutarı</th>
                    </tr>`;
    }
    $('#inTypeThead').html(html);
    createPayInTableDatas(data);
}

function createPayInTableDatas(data) {
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


        time = saat + ":" + dakika + ":" + saniye;
        date = date.day + "/" + date.month + "/" + date.year;

        html += `           <tr role="row" class="odd">
                            <td>` + item.pa_id + `</td>
                            <td>` + item.voucherNo + `</td>
                            <td>` + item.cu_code + `</td>
                            <td>` + item.cu_name + `</td>
                            <td>` + time + `</td>
                            <td>` + date + `</td>
                            <td>` + item.pa_paid + ` ₺</td>
                            <td>` + item.in_balance + ` ₺</td>
                            <td>` + item.pa_detail + `</td>
                            <td>` + item.in_total + `₺</td>
                            </tr>`;
    }
    $('#inTypeTbody').html(html);
}

function createPayOutTable(data) {
    let html = ``;
    if (data.length > 0) {
        html += `    <tr>
                        <th>PaymentOut Id</th>
                        <th>Başlık</th>
                        <th>Ödeme Türü</th>
                        <th>Ödeme Detayı</th>
                        <th>Ödeme Tutarı</th>
                        <th>Ödeme Tarihi</th>
                        <th>Ödeme Zamanı</th>
                        <th>İşlemi Gerçekleştiren Admin ID</th>
                    </tr>`;
    }
    $('#outTypeThead').html(html);
    createPayOutTableDatas(data);
}

function createPayOutTableDatas(data) {
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
                            <td>` + item.op_type + `</td>
                            <td>` + item.op_detail + `</td>
                            <td>` + item.op_total + ` ₺</td>
                            <td>` + date + `</td>
                            <td>` + time + `</td>
                            <td>` + item.admin.ad_id + `</td>
                            </tr>`;
    }
    $('#outTypeTbody').html(html);
}
