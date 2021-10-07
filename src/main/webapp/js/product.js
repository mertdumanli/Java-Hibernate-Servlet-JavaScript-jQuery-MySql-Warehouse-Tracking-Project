//Product
// add - start
let select_id = 0
$('#product_add_form').submit((event) => {
    event.preventDefault();

    const pr_title = $("#pr_title").val()
    const pr_purchasePrice = $("#pr_purchasePrice").val()
    const pr_salePrice = $("#pr_salePrice").val()
    const pr_code = $("#pr_code").val()
    const pr_vat = $("#pr_vat").val()
    const pr_unit = $("#pr_unit").val()
    const pr_amount = $("#pr_amount").val()
    const pr_detail = $("#pr_detail").val()

    const obj = {
        pr_title: pr_title,
        pr_purchasePrice: pr_purchasePrice,
        pr_salePrice: pr_salePrice,
        pr_code: pr_code,
        pr_vat: pr_vat,
        pr_unit: pr_unit,
        pr_amount: pr_amount,
        pr_detail: pr_detail,
    }
    if (select_id != 0) {
        // update
        obj["pr_id"] = select_id;
    }

    $.ajax({
        url: './product-post',
        type: 'POST',
        data: {obj: JSON.stringify(obj)},
        dataType: 'JSON',
        success: function (data) {
            if (data > 0) {
                alert("İşlem Başarılı")
                fncReset();
            } else {
                alert("İşlem sırasında hata oluştu!");
            }
        },
        error: function (err) {
            console.log(err)
            alert("İşlem işlemi sırasında bir hata oluştu!");
        }
    })
})
// add - end

// all product list - start

function allProduct() {
    $.ajax({
        url: './product-get',
        type: 'GET',
        dataType: 'Json',
        success: function (data) {
            createRow(data);
        },
        error: function (err) {
            console.log(err)
        }
    })
}

let globalArr = []

function createRow(data) {
    globalArr = data;
    let html = ``
    for (let i = 0; i < data.length; i++) {
        const itm = data[i];
        if (itm.pr_isAvailable == true) {

            const strVat = convertVat(itm.pr_vat);
            const strUnit = convertUnit(itm.pr_unit);

            html += `<tr role="row" class="odd">
            <td>` + itm.pr_id + `</td>
            <td>` + itm.pr_title + `</td>
            <td>` + itm.pr_purchasePrice + `</td>
            <td>` + itm.pr_salePrice + `</td>
            <td>` + itm.pr_code + `</td>
            <td>` + strVat + `</td>
            <td>` + strUnit + `</td>
            <td>` + itm.pr_amount + `</td>
            <td>` + itm.pr_detail + `</td>
            <td class="text-right" >
              <div class="btn-group" role="group" aria-label="Basic mixed styles example">
                <button onclick="fncProductDelete(` + itm.pr_id + `)" type="button" class="btn btn-outline-primary "><i class="far fa-trash-alt"></i></button>
                <button onclick="fncProductDetail(` + i + `)" data-bs-toggle="modal" data-bs-target="#productDetailModel" type="button" class="btn btn-outline-primary "><i class="far fa-file-alt"></i></button>
                <button onclick="fncProductUpdate(` + i + `)" type="button" class="btn btn-outline-primary "><i class="fas fa-pencil-alt"></i></button>
              </div>
            </td>
          </tr>`;
        }
    }
    $('#tableRow').html(html);
}

allProduct();
// all product list - end


// reset fnc
function fncReset() {
    select_id = 0;
    $('#product_add_form').trigger("reset");
    codeGenerator();
    allProduct();
}

// product delete - start
function fncProductDelete(pr_id) {
    let answer = confirm("Silmek istediğinizden emin misniz?");
    if (answer) {
        $.ajax({
            url: './product-delete?pr_id=' + pr_id,
            type: 'DELETE',
            dataType: 'text',
            success: function (data) {
                if (data != "0") {
                    fncReset();
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

// product delete - end


// product detail - start
function fncProductDetail(i) {
    const itm = globalArr[i];
    $("#pr_titleModal").text(itm.pr_title.toUpperCase()); //item içindeki cu_namei jspde cu_name e atar
    $("#pr_purchasePriceModal").text(itm.pr_purchasePrice == "" ? '------' : itm.pr_purchasePrice);
    $("#pr_salePriceModal").text(itm.pr_salePrice == "" ? '------' : itm.pr_salePrice);
    $("#pr_codeModal").text(itm.pr_code == "" ? '------' : itm.pr_code);
    $("#pr_vatModal").text(convertVat(itm.pr_vat));
    $("#pr_unitModal").text(convertUnit(itm.pr_unit));
    $("#pr_amountModal").text(itm.pr_amount == "" ? '------' : itm.pr_amount);
    $("#pr_detailModal").text(itm.pr_detail == "" ? '------' : itm.pr_detail);
}

// product detail - end


// product update - start
function fncProductUpdate(i) {
    const itm = globalArr[i];
    select_id = itm.pr_id;
    $("#pr_title").val(itm.pr_title)
    $("#pr_purchasePrice").val(itm.pr_purchasePrice)
    $("#pr_salePrice").val(itm.pr_salePrice)
    $("#pr_code").val(itm.pr_code)
    $("#pr_vat").val(itm.pr_vat)
    $("#pr_unit").val(itm.pr_unit)
    $("#pr_amount").val(1)
    $("#pr_detail").val(itm.pr_detail)
}

// product update - end

function codeGenerator() {
    const date = new Date();
    const time = date.getTime();
    const key = time.toString().substring(4);
    $('#pr_code').val(key)
    return key;
}

function beginForm(key) {
    const itm = globalArr;
    let i;
    for (i = 0; i < itm.length; i++) {
        if (key == itm[i].pr_code) {
            if (itm[i].pr_isAvailable == false) {
                alert("Bu ürün numarası eski bir ürüne aittir." +
                    "Geri eklenebilmesi rahat olması amacıyla bilgiler ürün ekle paneline girildi.")
            }
            fncProductUpdate(i)
            break;
        }
    }
    if (i == itm.length) {
        select_id = 0;
    }
}


$("#pr_code").keyup(function () {
    const key = $("#pr_code").val();
    beginForm(key);
})
beginForm(codeGenerator());

//Ürün Alış Fiyatı > 0
$("#pr_purchasePrice").keyup(function () {
    const value = $("#pr_purchasePrice").val();
    if (value < 1) {
        $("#pr_purchasePrice").val(1);
    }
})

//Ürün satış fiyatı > ürün alış fiyatı
$("#pr_salePrice").keyup(function () {
    const value = $("#pr_salePrice").val();//satış fiyat
    const value2 = $("#pr_purchasePrice").val();//alış fiyat
    if (parseInt(value) <= parseInt(value2)) {
        $("#pr_salePrice").val(parseInt(value2) + 1);
    }
})

//Ürün alış fiyatı < ürün satış fiyatı
$("#pr_purchasePrice").keyup(function () {
    const value = $("#pr_salePrice").val();//satış fiyat
    const value2 = $("#pr_purchasePrice").val();//alış fiyat
    if (parseInt(value) <= parseInt(value2)) {
        $("#pr_purchasePrice").val(parseInt(value) - 1);
    }
})

//Miktar > 0
$("#pr_amount").keyup(function () {
    const value = $("#pr_amount").val();
    if (value < 1) {
        $("#pr_amount").val(1);
    }
})

//KDV String ifadeye çevirme
function convertVat(vat) {
    if (vat == 1) {
        return "Dahil"
    } else if (vat == 2) {
        return "%1"
    } else if (vat == 3) {
        return "%8"
    } else {
        return "%18"
    }
}

//Birim String ifadeye çevirme
function convertUnit(unit) {
    if (unit == 1) {
        return "Adet"
    } else if (unit == 2) {
        return "KG"
    } else if (unit == 3) {
        return "Metre"
    } else if (unit == 4) {
        return "Paket"
    } else {
        return "Litre"
    }
}