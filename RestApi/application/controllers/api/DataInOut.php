<?php defined('BASEPATH') OR exit('No direct script access allowed');

use Restserver\Libraries\REST_Controller;

require APPPATH . '/libraries/REST_Controller.php';

class DataInOut extends REST_Controller {
    public function __construct() {
        parent::__construct();
        // Load Model
        // $this->load->model('DataModel', 'data');
    }

    public function index_get()
    {
        $data_inout = $this->db->get('data_inout')->result_array();
        if ($data_inout) {
            $this->response([
                'status' => TRUE,
                'data' => $data_inout
            ], REST_Controller::HTTP_OK);
        } else {
            $this->response([
                'status' => FALSE,
                'message' => 'Data In / Out Tidak Ditemukan'
            ], REST_Controller::HTTP_NOT_FOUND);
        }
    }
} 