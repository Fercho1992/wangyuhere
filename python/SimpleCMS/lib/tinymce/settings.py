import os
from SimpleCMS import settings

DEFAULT_CONFIG = getattr(settings, 'TINYMCE_DEFAULT_CONFIG',
        {'theme': "simple", 'relative_urls': False})

USE_SPELLCHECKER = getattr(settings, 'TINYMCE_SPELLCHECKER', False)

USE_COMPRESSOR = getattr(settings, 'TINYMCE_COMPRESSOR', False)

USE_FILEBROWSER = getattr(settings, 'TINYMCE_FILEBROWSER',
        'filebrowser' in settings.INSTALLED_APPS)

JS_URL = getattr(settings, 'TINYMCE_JS_URL',
        '%sjs/tiny_mce/tiny_mce.js' % settings.MEDIA_URL)

JS_ROOT = getattr(settings, 'TINYMCE_JS_ROOT',
        os.path.join(settings.MEDIA_ROOT, 'js/tiny_mce'))

JS_BASE_URL = JS_URL[:JS_URL.rfind('/')]
