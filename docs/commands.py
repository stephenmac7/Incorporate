import json
from mako.template import Template

def main():
    data = json.loads(open('commands.json').read())
    print(Template(filename='commands.mako').render(data=data))

if __name__ == '__main__':
    main()
