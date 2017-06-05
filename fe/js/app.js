(function (window) {
    'use strict';

    function checkNum() {
        var list = $('.todo-list li');
        var num = 0;
        $.each(list, function (idx, val) {
            if (val.className === '') {
                num++;
            }
        });
        $('footer .todo-count strong').text(num);
    }

    $(document).ready(function () {
        var className = '';
        var ck = '';
        $.ajax({
            type: 'GET',
            url: '/tasks',
            dataType: 'JSON',
            success: function (data) {
                var min = 0;
                $.each(data, function (idx, val) {
                        if (val.completed === 1) {
                            className = 'completed';
                            ck = 'checked';
                        } else {
                            className = '';
                            ck = '';
                        }
                        var t = $(".todo-list").append('<li class=' + className + '>' + '<div class="view">' + '<input class="toggle" type="checkbox"' + ck + '>' + '<label>' + val.todo + '</label>' + '<button class="destroy">' + '</button>' + '</div>' + '<input class="edit" value=' + val.id + '>' + '</li>');

                    }

                );
                checkNum();
            }
        });
    });
    $(document).on('click', '.toggle', function (e) {
        var curr = $(this);
        if ($(this).is(':checked')) {
            $.ajax({
                type: 'put',
                url: '/task/' + $(this).parent().siblings('.edit').val(),
                contentType: "application/json",
                data: JSON.stringify({
                    'completed': 1
                }),
                success: function () {
                    curr.parents('li').addClass('completed');
                    checkNum();
                }
            });
        } else {
            $.ajax({
                type: 'put',
                url: '/task/' + $(this).parent().siblings('.edit').val(),
                contentType: "application/json",
                data: JSON.stringify({
                    'completed': 0
                }),
                success: function () {
                    curr.parents('li').removeClass();
                    checkNum();
                }
            });

        }
        // ajax put
    });
    $(document).on('click', '.destroy', function (e) {
        var curr = $(this);
        $.ajax({
            type: 'DELETE',
            url: '/task/' + $(this).parent().siblings('.edit').val(),
            success: function () {
                curr.parents('li').remove();
                checkNum();
            }
        });

    });

    $(".new-todo").on("keypress", function (e) {
        var data = {
            'id': '',
            'todo': '',
            'completed': '',
            'date': ''
        };
        if (e.which === 13) {

            data.todo = $(this).val();
            if (data.todo === '')
                return;
            data.completed = 0;
            data.date = new Date();
            $.ajax({
                type: 'POST',
                url: '/task',
                contentType: "application/json",
                data: JSON.stringify(data),
                dataType: 'JSON',
                error: function (xhr, status, error) {
                    alert(error);
                },
                success:

                    function (ndata) {
                        $(".todo-list").prepend('<li>' + '<div class="view">' + '<input class="toggle" type="checkbox">' + '<label>' + ndata.todo + '</label>' + '<button class="destroy">' + '</button>' + '</div>' + '<input class="edit" value=' + ndata.id + '>' + '</li>');

                        checkNum();
                    }
            });

            // ajax post
        }
    });


    $('.filters li a').on('click', function (e) {
        e.preventDefault();
        var h = this.hash.split('#/')[1];
        if (h == '')
            h = 'none';
        var className;
        $.ajax({
            type: 'GET',
            url: '/filter/' + h,
            contentType: "application/json",
            dataType: 'JSON',
            success: function (data) {
                $('.todo-list').children().remove();
                var ck;
                $.each(data, function (idx, val) {
                        if (val.completed === 1) {
                            className = 'completed';
                            ck = 'checked';
                        } else {
                            className = '';
                            ck = '';
                        }
                        var t = $(".todo-list").append('<li class=' + className + '>' + '<div class="view">' + '<input class="toggle" type="checkbox" ' + ck + '>' + '<label>' + val.todo + '</label>' + '<button class="destroy">' + '</button>' + '</div>' + '<input class="edit" value=' + val.id + '>' + '</li>');
                    }

                );
                checkNum();
            }
        })
    })

    $('.clear-completed').on('click', function (e) {
        var arr = [];
        var completed = [];
        var list = $('.todo-list').children();
        $.each(list, function (idx, val) {
            var $v = $(val);
            if ($v.attr('class') === 'completed') {
                arr.push($v.children('.edit').val());
                completed.push($v);
            }

        });
        $.ajaxSettings.traditional = true;
        $.ajax({
            type: 'DELETE',
            url: '/completed',
            data: JSON.stringify(arr),
            contentType: "application/json",
            success: function () {
                $.each($(completed), function (idx, val) {
                    val.remove();
                });
            }


        })

    })

})(window);
