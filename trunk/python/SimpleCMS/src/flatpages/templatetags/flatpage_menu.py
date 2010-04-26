from django import template
from django.contrib.flatpages.models import FlatPage

register = template.Library()

def flatpage_menu():
    menu = _get_menus(1)
    t = template.loader.get_template('flatpages/menu.html')
    return t.render(template.Context({'menu':menu, 'has_menu':len(menu)>0}))

def flatpage_submenu(page_url):
    submenu = _get_menus(2, page_url)
    t = template.loader.get_template('flatpages/submenu.html')
    return t.render(template.Context({'submenu':submenu, 'has_submenu':len(submenu)>0 }))

def _get_menus(level, page_url=''):
    pages = FlatPage.objects.all()
    menu = []
    
    for i in range(len(pages)):
        paths = pages[i].url.split('/')
        while('' in paths):
            paths.remove('')
        if((len(paths) == level and level == 1) or (len(paths) == level and level == 2 and page_url.startswith('/'+paths[0])) ):
            menu.append({'url':pages[i].url, 'title':pages[i].title})

    return menu

register.simple_tag(flatpage_menu)
register.simple_tag(flatpage_submenu)

