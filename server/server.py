import sys
import socket
import threading

def process_receiving(c, addr):
    while True:
        data = c.recv(1440)
        # print(data)
        if not data:
            break
    c.close()
    print('Disconnected', addr)

if __name__ == '__main__':
    s = socket.socket()
    port = 8888
    s.bind(('', port))
    s.listen(5)
    print('Start Server with port %d' % (port))
    while True:
        c, addr = s.accept()
        print('Connected by', addr)
        t = threading.Thread(target=process_receiving, args=(c, addr))
        t.start()

        


