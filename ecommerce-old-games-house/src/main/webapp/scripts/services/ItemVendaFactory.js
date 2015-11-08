angular.module('ecommerceoldgameshouse').factory('ItemVendaResource', function($resource){
    var resource = $resource('rest/itemvendas/:ItemVendaId',{ItemVendaId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});