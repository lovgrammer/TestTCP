import pandas as pd
import numpy as np

data = pd.read_csv('data/trace.csv')
data.columns = 'time length'.split(' ')

format_string = "%b %d, %Y %H:%M:%S.%f KST"

data['time'] = pd.to_datetime(data['time'], format=format_string)
data['time'] = data['time'].astype(np.int64)

data.to_csv('data/trace_fixed.csv', index=False, header=False)
