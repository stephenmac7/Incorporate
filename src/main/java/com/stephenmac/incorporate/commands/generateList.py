from glob import glob

for filename in glob('*'):
    if '.java' in filename and filename != 'Command.java' and filename != 'PlayerContextCommand.java':
        print("processCommand(" + filename.split('.')[0] + '.class);')
