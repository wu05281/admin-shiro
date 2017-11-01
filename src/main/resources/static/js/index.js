/**
 * 用来计算frame的高度.
 */
var indexModel = (function () {
    return {
        initFrameHeight : function () {
            var window_height = $(window).height();
            var header_height = $('.main-header').outerHeight();
            var footer_height = $('.main-footer').outerHeight();
            $("#mainFrame").height(window_height - header_height - footer_height-10);

            // var content_header = $('.content-header').outerHeight();
            // $("#mainFrame").height(window_height - header_height - content_header - footer_height-10);
            //$("#mainFrame").contents().find("body").css('min-height', window_height - $('.main-footer').outerHeight());
        }

    }
})();

(function () {
    indexModel.initFrameHeight();
})();