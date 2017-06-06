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



//---------------------------devices----------------------------


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

function initListItemListen(){

    $(".devices #devicesModel .insert_task .list-group-item").on("click",function(e){
        var current = $(e.currentTarget);
        var tasksId = current.attr("task_id");
        var selectItem = $('#devices_table .data_item [name="selectBox"]:checked');
        var type = $('#devicesModel [name="insert_type"]').val();
        var devIds ="";
        if(selectItem && selectItem.length>0){
            for(var i=0;i<selectItem.length;i++){
                var itemId = $(selectItem[i]).attr("item_id");
                if(itemId){
                    if(i==0){
                        devIds +=itemId;
                    }else{
                        devIds +=","+itemId;
                    }
                }
            }
        }
        $.post("/games/insertDevicesTasks",{
            tasksId:tasksId,
            type:type,
            devIds:devIds
        },function(data){
            if(data.code&&data.code==200){
                alert("插入成功");
                location.href=location.href.replace("#devicesModelEdit","");
            }else{
                alert(data.msg);
            }
        });

    })
}

$('.devices #add_devices_taks').on('click',function(e){
    var current = $(e.currentTarget);
    var gamesAlias=current.attr('gamesAlias');
    $.post("/games/getTaskByGamesAlias",{
        gamesAlias:gamesAlias
    },function(data){
        if(data.code&&data.code==200){
            $('.devices .insert_task').html('');
            if(data.data && data.data.length>0){
                var list = data.data;
                for(var i=0;i<list.length;i++){
                    $('.devices .insert_task').append(' <li class="list-group-item" task_id="'+list[i].id+'">'+list[i].taskName+'-'+list[i].methodName+'</li>');
                }
                initListItemListen();
            }
        }else{
            alert(data.msg);
        }
    });
})


//---------------------------task----------------------------

$(".tasks .add_tasks").on("click",function(e){
    var gamesId = $('#add_tasks').attr("gamesId");
    var tasksName = $('#tasksModel [name="tasksName"]').val();
    var methodName = $('#tasksModel [name="methodName"]').val();
    $.post("/games/addTasks",{
        gamesId:gamesId,
        methodName:methodName,
        tasksName:tasksName
    },function(data){
        if(data.code&&data.code==200){
            window.location.reload();
        }
    });
})

$('.tasks .data_item .delete').on("click",function(e){
    var current = $(e.currentTarget);
    var id = current.parent().parent().find('[name="selectBox"]').attr("item_id");
    if(id){
        $.post("/games/deleteTasks",{
            id:id
        },function(data){
            if(data.code!=200){
                alert("操作失败:"+data.msg);
            }else{
                window.location.reload();
            }
        });
    }
})

$('.tasks .data_item .edit_').on("click",function(e){
    var current = $(e.currentTarget);
    var id = current.parent().parent().find('[name="selectBox"]').attr("item_id");
    if(id){
        $('#tasksModelEdit').data("tasksId",id);
    }
})

$(".tasks .edit_tasks").on("click",function(e){
    var tasksName = $('#tasksModelEdit [name="tasksName"]').val();
    var methodName = $('#tasksModelEdit [name="methodName"]').val();
    $.post("/games/editTasks",{
        id:$('#tasksModelEdit').data("tasksId"),
        tasksName:tasksName,
        methodName:methodName
    },function(data){
        if(data.code&&data.code==200){
            window.location.reload();
        }
    });

})




//---------------------------taskQueue----------------------------

function initTasksQueueListItemListen(devicesId){

    $(".tasksQueue #tasksQueueModel .insert_tasksQueue .list-group-item").on("click",function(e){
        var current = $(e.currentTarget);
        var tasksId = current.attr("task_id");
        var type = $('#tasksQueueModel [name="insert_type"]').val();
        $.post("/games/addTasksQueue",{
            tasksId:tasksId,
            type:type,
            devicesId:devicesId
        },function(data){
            if(data.code&&data.code==200){
                alert("插入成功");
                window.location.reload();
            }else{
                alert(data.msg);
            }
        });
    })
}

$('.tasksQueue #add_tasksQueue').on('click',function(e){
    var current = $(e.currentTarget);
    var devicesId=current.attr('devicesId');
    var gamesAlias=current.attr('gamesAlias');
    $.post("/games/getTaskByGamesAlias",{
        gamesAlias:gamesAlias
    },function(data){
        if(data.code&&data.code==200){
            $('.tasksQueue .insert_tasksQueue').html('');
            if(data.data && data.data.length>0){
                var list = data.data;
                for(var i=0;i<list.length;i++){
                    $('.tasksQueue .insert_tasksQueue').append(' <li class="list-group-item" task_id="'+list[i].id+'">'+list[i].taskName+'-'+list[i].methodName+'</li>');
                }
                initTasksQueueListItemListen(devicesId);
            }
        }else{
            alert(data.msg);
        }
    });
})


$('.tasksQueue .data_item .delete').on("click",function(e){
    var current = $(e.currentTarget);
    var id = current.parent().parent().find('[name="selectBox"]').attr("item_id");
    if(id){
        $.post("/games/deleteTasksQueue",{
            id:id
        },function(data){
            if(data.code!=200){
                alert("操作失败:"+data.msg);
            }else{
                window.location.reload();
            }
        });
    }
})