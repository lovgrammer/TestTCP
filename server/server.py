import sys
import socket
import threading

def process_uplink(c, addr):
    recv_size = 0
    while True:
        data = c.recv(5000)
        recv_size = recv_size + len(data)
        print('process_uplink %d' % (recv_size), end="\r")
        # print(data)
        if not data:
            break
    c.close()
    print('Disconnected', addr)

def process_downlink(c, addr):
    send_size = 0    
    while True:
        try:
            c.send(bytes(5000))
            send_size = send_size + 5000
            print('process_uplink %d' % (send_size), end="\r")
        except:
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
        data = c.recv(5000)
        print('data', data[0])
        if 'u' in str(data):
            print('Uplink')
            t = threading.Thread(target=process_uplink, args=(c, addr))
            t.start()
        elif 'd' in str(data):
            print('Downlink')
            t = threading.Thread(target=process_downlink, args=(c, addr))
            t.start()
