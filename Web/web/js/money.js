var Money = {
    ajax: {
        get: function($http, url, data, callbackFn) {
            $http.get(url, {params: data}).
              success(function(data, status, headers, config) {
                callbackFn.call(this, data);
                
              }).
              error(function(data, status, headers, config) {
                console.log("Ajax get error with url : " + url);
              });
        },
        post: function($http, url, data, callbackFn) {
            $http.post(url, data).
              success(function(data, status, headers, config) {
                callbackFn.call(this, data);
                
              }).
              error(function(data, status, headers, config) {
                console.log("Ajax post error with url : " + url);
                console.log(data);
              });
        }
    }
};