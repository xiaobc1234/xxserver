/**
 * Created by xiaobc on 17/6/4.
 */

//---------------------------game----------------------------

$(".select_all").on("click",function(e){
    var item = $('.data_item').find('[name="selectBox"]');
    var itemChecked = $('.data_item').find('[name="selectBox"]:checked');
    if(itemChecked && itemChecked.length>0){
        item.attr("checked",false);
    }else{
        item.attr("checked",true);
    }
})

var gamesTable = $('#games_table');


$(".games .add_games").on("click",function(e){
    var gamesName = $('[name="gamesName"]').val();
    var alias = $('[name="gamesAlias"]').val();
    $.post("/games/addGames",{
        gamesName:gamesName,
        alias:alias
    },function(data){
        if(data.code&&data.code==200){
            location.href="/games/games";
        }
    });
})

$('.games .data_item .delete').on("click",function(e){
    var current = $(e.currentTarget);
    var id = current.parent().parent().find('[name="selectBox"]').attr("item_id");
    if(id){
        $.post("/games/deleteGames",{
            id:id
        },function(data){
            if(data.code!=200){
                alert("操作失败:"+data.msg);
            }else{
                location.href="/games/games";
            }
        });
    }
})

$('.games .data_item .edit_').on("click",function(e){
    var current = $(e.currentTarget);
    var id = current.parent().parent().find('[name="selectBox"]').attr("item_id");
    if(id){
        $('#gamesModelEdit').data("gamesId",id);
    }
})

$(".games .edit_games").on("click",function(e){
    var gamesName = $('[name="gamesNameEdit"]').val();
    $.post("/games/editGames",{
        id:$('#gamesModelEdit').data("gamesId"),
        gamesName:gamesName
    },function(data){
        if(data.code&&data.code==200){
            //location.href="/games/games";
            location.href=location.href.replace("#gamesNameEdit","");
        }
    });

})



//---------------------------task----------------------------


$('.devices .data_item .edit_').on("click",function(e){
    var current = $(e.currentTarget);
    var id = current.parent().parent().find('[name="selectBox"]').attr("item_id");
    if(id){
        $('#devicesModelEdit').data("devicesId",id);
    }
})

$(".devices .edit_devices").on("click",function(e){
    var deviceAlias = $('[name="deviceAlias"]').val();
    $.post("/games/editDevices",{
        id:$('#devicesModelEdit').data("devicesId"),
        alias:deviceAlias
    },function(data){
        if(data.code&&data.code==200){
            location.href=location.href.replace("#devicesModelEdit","");
        }
    });

})




