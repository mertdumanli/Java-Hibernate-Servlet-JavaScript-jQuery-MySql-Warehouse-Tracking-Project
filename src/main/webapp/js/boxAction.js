let selectedCustomer = 0; //cu_id
let selectedProduct = 0; //pr_id
let maxNumber = 0;

$('#customer_select').change(function () {
    selectedCustomer = this.value;
    xOrder();//Kullanıcının siparişlerini yazdırma.
    if (selectedCustomer == 0) {
        codeGenerator()
    }
})

$('#product_select').change(function () {
    selectedProduct = this.value;
    if (selectedProduct != 0) {
        xProduct();
    }
})

$('#newSale').submit((event) => {
    event.preventDefault();

    const customer_select = selectedCustomer;
    const product_select = selectedProduct;
    const count = $("#count").val()
    const voucherNumber = $("#voucherNumber").val()

    const obj = {
        customer_select: customer_select,
        product_select: product_select,
        count: count,
        voucherNumber: voucherNumber,
    }

    $.ajax({
        url: './box-action-post',
        type: 'POST',
        data: {obj: JSON.stringify(obj)},
        dataType: 'JSON',
        success: function (data) {
            if (data > 0) {
                alert("İşlem Başarılı")

                $('#product_select').val(0);
                $('#product_select').selectpicker("refresh");
                selectedProduct = 0;

                xOrder()//Ürünler tablosunu refresh
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

// add - end

function changeMaxNumber() {
    $("#count").val(globalProduct.pr_amount);
    $("#count").attr('max', globalProduct.pr_amount);
    maxNumber = globalProduct.pr_amount;
    if (maxNumber == 0) {
        $("#count").attr('min', 0)
    }
}

//Ürün Adeti > 0
$("#count").keyup(function () {
    const value = $("#count").val();
    if (value < 1) {
        $("#count").val(1);
    } else if (value > maxNumber) {
        $("#count").val(maxNumber);
    }
})

$("#voucherNumber").keyup(function () {
    $("#voucherNumber").val(vouc);
    $("#voucherNumber").attr("disabled", true)

})

function codeGenerator() {
    const date = new Date();
    const time = date.getTime();
    const key = time.toString().substring(4);
    $('#voucherNumber').val(key)
}

codeGenerator()

let globalPurchaseOrders = [];//Tüm satış geçmişi

function xOrder() {
    $.ajax({
        url: './purchase-orders-get?purchase_ara=' + selectedCustomer,
        type: 'GET',
        dataType: 'Json',
        success: function (data) {
            globalPurchaseOrders = data;
            createRow();
        },
        error: function (err) {
            console.log(err)
        }
    })
}


let globalProduct;

function xProduct() {
    $.ajax({
        url: './get-single-product-servlet?ara=' + selectedProduct,
        type: 'GET',
        dataType: 'Json',
        success: function (data) {
            globalProduct = data;
            changeMaxNumber();//Max girilebilir adet ayarlama.
        },
        error: function (err) {
            console.log(err)
        }
    })
}

let vouc = 0;

function createRow() {
    const orders = globalPurchaseOrders;

    let html = ``;
    vouc = 0;


    for (let i = 0; i < orders.length; i++) {
        const item = orders[i];
        html += `           <tr role="row" class="odd">
           <td>` + item.po_id + `</td>
                            <td>` + item.product.pr_title.toUpperCase() + `</td>
                            <td>` + item.number + `</td>
                            <td>` + item.product.pr_salePrice + `</td>
                            <td>` + parseInt(item.product.pr_salePrice) * parseInt(item.number) + ` ₺</td>
                            <td>` + convertVat(item.product.pr_vat) + `</td>
                            <td>` + item.voucherNo + `</td>
                            <td class="text-right">
                                <div class="btn-group" role="group" aria-label="Basic mixed styles example">
                                    <button onclick="fncDelete(` + item.po_id + `)"  type="button" class="btn btn-outline-primary "><i class="far fa-trash-alt"></i>
                                    </button>
                                </div>
                            </td>
                            </tr>`;
    }
    if (orders.length != 0) {
        vouc = orders[0].voucherNo;
    }
    $('#sepet').html(html);

    //Fiş no ile sepetteki ürünlerin fiş nosu eşitlenecek.
    if (vouc != 0) {
        $("#voucherNumber").val(vouc)
    } else {
        codeGenerator()//Siparişi bulunmayan bir kişi seçilirse.(Öncesinde başka bir kişinin fiş numarası kaldıysa resetlemek için.)
    }
}

// product delete - start
function fncDelete(po_id) {
    let answer = confirm("Silmek istediğinizden emin misniz?");
    if (answer) {
        $.ajax({
            url: './box-action-delete?po_id=' + po_id,
            type: 'DELETE',
            dataType: 'text',
            success: function (data) {
                if (data != "0") {
                    xOrder();
                    xProduct();
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

$("#complete").click(function () {
    let answer = confirm("Sipariş tamamlanacak emin misiniz?");
    if (answer) {
        const obj = {
            voucherNumber: $("#voucherNumber").val(),
        }

        $.ajax({
            url: './purchase-orders-post',
            type: 'POST',
            data: {obj: JSON.stringify(obj)},
            dataType: 'JSON',
            success: function (data) {
                if (data > 0) {
                    alert("Sipariş Tamamlandı <-> Fiş No : " + $("#voucherNumber").val())
                    xOrder();//Sepeti yenile.
                } else {
                    alert("İşlem sırasında hata oluştu! Sepetiniz boş.");
                }
            },
            error: function (err) {
                console.log(err)
                alert("İşlem işlemi sırasında bir hata oluştu!");
            }
        })
    }
})