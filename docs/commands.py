import json
import requests
import sys
from mako.template import Template

def main():
    data = json.loads(open('commands.json').read())
    text = Template(filename=sys.argv[1]).render(data=data)
    payload = {'markup_type': 'html', 'markup': text, 'comments': 'y'}
    requests.post('http://dev.bukkit.org/bukkit-plugins/incorporate/pages/main/edit/?api-key='+sys.argv[2], payload)

if __name__ == '__main__':
    main()
