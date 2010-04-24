from django.conf.urls.defaults import *

urlpatterns = patterns('mysite.flatpages.views',
    (r'^(?P<url>.*)$', 'flatpage'),
)
