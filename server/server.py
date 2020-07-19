import os
import sys
import socket
import threading

def start_tcpdump(sname, iname, hname, port):
    cmd = 'tcpdump -ttt -w data/trace_%s.pcap -i %s dst %s and dst port %d &' % (sname, iname, hname, port)
    # cmd = str(cmd)
    # print(cmd)
    # cmd = 'tcpdump -ttt -w data/trace_%s.pcap &' % ('hello')
    sudoPassword = 'asdf1234'
    p = os.system('echo %s|sudo -S %s' % (sudoPassword, cmd))

def stop_tcpdump():
    os.system("kill -9 `ps -aux | grep tcpdump | awk '{print $2}'`")
    
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
    stop_tcpdump()

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
    stop_tcpdump()    
    
if __name__ == '__main__':

    if len(sys.argv) != 2:
        print('Input parameter is not avlid')
        print('example) python3 server.py <interface:wlan0>')
        exit()
    else:
        iname = sys.argv[1]
        
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
        
        sname = data[1:30].decode("ascii").strip().strip('\x00')
        
        start_tcpdump(sname, iname, addr[0], addr[1])
        
        if 'u' in str(data[0:1]):
            print('Uplink')
            t = threading.Thread(target=process_uplink, args=(c, addr))
            t.start()
        elif 'd' in str(data[0:1]):
            print('Downlink')
            t = threading.Thread(target=process_downlink, args=(c, addr))
            t.start()
