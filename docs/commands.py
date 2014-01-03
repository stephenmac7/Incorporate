import json
import sys
from mako.template import Template

def main():
    data = json.loads(open('commands.json').read())
    print(Template(filename=sys.argv[1]).render(data=data))

if __name__ == '__main__':
    main()
