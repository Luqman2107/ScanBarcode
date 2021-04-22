<?php defined('BASEPATH') OR exit('No direct script access allowed');

use Restserver\Libraries\REST_Controller;

require APPPATH . '/libraries/REST_Controller.php';

class TrnScanBarcode extends REST_Controller {
    public function __construct() {
        parent::__construct();
        // Load Model
        $this->load->model('DataModel', 'data');
    }

    public function index_get()
    {
        $sessionid = $this->get('SessionId');
        if ($sessionid == '') {
            die;
        } else {
            $this->db->where('SessionId', $sessionid);
            $trn_scanbarcode = $this->db->get('trn_scanbarcode')->result_array();
        }
        
        if ($trn_scanbarcode) {
            $this->response([
                'status' => TRUE,
                'data' => $trn_scanbarcode
            ], REST_Controller::HTTP_OK);
        } else {
            $this->response([
                'status' => FALSE,
                'pesan' => 'Data Not Found!'
            ], REST_Controller::HTTP_NOT_FOUND);
        }
    }

    public function index_post()
    {
        
        $adm_name = $this->input->post('Adm_Name');
        $line_no = $this->input->post('Line_No');
        $station_name = $this->input->post('Station_Name');
        $io_name = $this->input->post('IO_Name');
        $barcodeno = $this->input->post('BarcodeNo');
        $sessionid = $this->input->post('SessionId');
        $barcodetype = $this->input->post('BarcodeType');
        $bartypecode = $this->input->post('BarcodeTypeCode');
        
        //$cek = $this->db->get_where('trn_scanbarcode', ['BarcodeNo' => $barcodeno], ['Adm_Name' => $adm_name], ['Line_No' => $line_no], ['Station_Name' => $station_name], ['IO_Name' => $io_name])->row_array();
        
        $cek = $this->db->get_where('trn_scanbarcode', ['BarcodeNo' => $barcodeno, 'Adm_Name' => $adm_name, 'Line_No' => $line_no, 'Station_Name' => $station_name, 'IO_Name' => $io_name])->row_array();




        if ($cek > 0) 
        {
            $response = 
            [
                'status' => false,
                'pesan' => 'Barcode has been scan before!'
            ];
            $this->response($response, 400);
        } else 
        {
            $arr = [
                'Adm_Name' => $this->input->post('Adm_Name'),
                'Line_No' => $this->input->post('Line_No'),
                'Station_Name' => $this->input->post('Station_Name'),
                'IO_Name' => $this->input->post('IO_Name'),
                'BarcodeNo' => $this->input->post('BarcodeNo'),
                'SessionId' => $this->input->post('SessionId'),
                'BarcodeType' => $this->input->post('BarcodeType'),
                'BarcodeTypeCode' => $this->input->post('BarcodeTypeCode'),
                'ScanDate' => date('Y-m-d H:i:s'),
            ];
            if ($this->data->insert('trn_scanbarcode', $arr)) {
                $response = [
                    'status' => true,
                    'pesan' => 'Successfully saved!',
                ];
            $this->response($response, 200);
            } else {
                $response = [
                    'status' => false,
                    'pesan' => 'Saved failed!',
                ];
            $this->response($response, 400);
            }
        }
    }
} 

