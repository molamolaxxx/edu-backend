// 填充所有token
function filledAllAuthorization(value){
    var list = document.getElementsByName("Authorization")
    for (i=0; i<list.length;i++){
        list[i].value = value
    }
}